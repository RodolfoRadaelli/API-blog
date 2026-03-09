import { useState } from 'react';
import { Modal, Form, Button, Alert } from 'react-bootstrap';
import api from '../../../api//axios';

interface RegisterModalProps {
	show: boolean;
	onHide: () => void;
}

const RegisterModal = ({ show, onHide }: RegisterModalProps) => {
	const [username, setUsername] = useState('');
	const [password, setPassword] = useState('');
	const [role, setRole] = useState('ROLE_USER');
	const [loading, setLoading] = useState(false);
	const [error, setError] = useState('');
	const [success, setSuccess] = useState(false);

	const handleSubmit = async (e: React.FormEvent) => {
		e.preventDefault();
		setError('');
		setSuccess(false);

		if (!username.trim() || !password.trim()) {
			setError('Usuario y contraseña son obligatorios');
			return;
		}
		setLoading(true);

		try {
			await api.post('/register/user', {
				username: username.trim(),
				password: password,
				role: role
			});

			setSuccess(true);

			setTimeout(() => {
				handleClose();
			}, 2000);

		} catch (err: any) {
			setError(err.response?.data?.message || 'Error al registrar');
		} finally {
			setLoading(false);
		}
	};

	const handleClose = () => {
		setUsername('');
		setPassword('');
		setRole('ROLE_USER');
		setError('');
		setSuccess(false);
		onHide();
	};

	return (
		<Modal show={show} onHide={handleClose}>
			<Modal.Header closeButton>
				<Modal.Title>Crear cuenta</Modal.Title>
			</Modal.Header>

			<Modal.Body>
				{error && <Alert variant="danger">{error}</Alert>}
				{success && (
					<Alert variant="success">
						¡Registrado! puedes inidicar sesión.
					</Alert>
				)}

				<Form onSubmit={handleSubmit}>
					<Form.Group className="mb-3">
						<Form.Label>Username</Form.Label>
						<Form.Control
							type="text"
							placeholder="Nombre de usuario"
							value={username}
							onChange={(e) => setUsername(e.target.value)}
							disabled={success}
						/>
					</Form.Group>

					<Form.Group className="mb-3">
						<Form.Label>Password</Form.Label>
						<Form.Control
							type="password"
							placeholder="Contraseña"
							value={password}
							onChange={(e) => setPassword(e.target.value)}
							disabled={success}
						/>
					</Form.Group>

					<Form.Group className="mb-3">
						<Form.Label>Rol</Form.Label>
						<Form.Select
							value={role}
							onChange={(e) => setRole(e.target.value)}
							disabled={success}
						>
							<option value="ROLE_USER">Usuario</option>
							<option value="ROLE_ADMIN">Administrador</option>
						</Form.Select>
					</Form.Group>

					<Button
						variant="primary"
						type="submit"
						className="w-100"
						disabled={loading || success}
					>
						{loading ? 'Registrando...' : 'Crear Cuenta'}
					</Button>
				</Form>
			</Modal.Body>
		</Modal>
	);
};
export default RegisterModal;

