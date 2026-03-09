import { Container, Card, Form, Button } from 'react-bootstrap'
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api//axios';
import RegisterModal from '../features/auth/components/RegisterModal';

const LoginPage = () => {
	const [user, setUser] = useState('');
	const [password, setPassword] = useState('');
	const [error, setError] = useState('');
	const [loading, setLoading] = useState(false);
	const [showRegister, setShowRegister] = useState(false);

	const navigate = useNavigate();

	const handleSubmit = async (e: React.FormEvent) => {
		e.preventDefault();
		setError('');
		setLoading(true);

		if (!user.trim()) {
			setError('Usuario es obligatorio');
			setLoading(false);
			return;
		}

		if (!password.trim()) {
			setError('Password es obligatorio');
			setLoading(false);
			return;
		}

		try {
			const response = await api.post('/authenticate', {
				username: user,
				password: password
			});

			const token = typeof response.data === 'string' ? response.data :
				response.data.token;

			localStorage.setItem('token', token);
			navigate('/dashboard');

		} catch (err: any) {
			const message = err.response?.data?.message || err.message || 'Error de conexión';
			setError(message);
		} finally {
			setLoading(false);
		}
	};



	return (
		<Container className="d-flex justify-content-center mt-5">
			<Card style={{ width: '20rem' }} className="shadow">
				<Card.Body>
					<Card.Title className="text-center mb-4">Login Form</Card.Title>

					{error && (
						<div className="alert alert-danger py-2" role="alert">
							{error}
						</div>
					)}

					<Form onSubmit={handleSubmit}>
						<Form.Group className="mb-3" controlId="Username">
							<Form.Label>Username</Form.Label>
							<Form.Control type="text" placeholder="Tu usuario" value={user} onChange={(e) => setUser(e.target.value)} isInvalid={!!error && !user.trim()} />
						</Form.Group>

						<Form.Group className="mb-3" controlId="Password">
							<Form.Label>Password</Form.Label>
							<Form.Control type="password" placeholder="Tu contraseña" value={password} onChange={(e) => setPassword(e.target.value)} isInvalid={!!error && !password.trim()} />
						</Form.Group>

						<Button variant="primary" type="submit" className="w-100" disabled={loading}>
							{loading ? 'cargando...' : 'submit'}
						</Button>
					</Form>

					<hr />

					<div className="text-center">
						<small className="text-muted d-block mb-2">
							¿No tienes cuenta?
						</small>
						<Button
							variant="outline-success"
							className="w-100"
							onClick={() => setShowRegister(true)}
						>
							Crear cuenta nueva
						</Button>
					</div>
				</Card.Body>
			</Card>

			<RegisterModal show={showRegister} onHide={() => setShowRegister(false)} />

		</Container>
	);
};

export default LoginPage;
