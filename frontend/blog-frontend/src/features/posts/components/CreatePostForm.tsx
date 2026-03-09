import { useState } from 'react';
import { Form, Button, Card, Alert } from 'react-bootstrap';
import api from '../../../api/axios';
import { getUsernameFromToken } from '../../../utils/jwt';

interface CreatePostFormProps {
	onPostCreated: () => void;
}

const CreatePostForm = ({ onPostCreated }: CreatePostFormProps) => {
	const [title, setTitle] = useState('');
	const [content, setContent] = useState('');
	const [loading, setLoading] = useState(false);
	const [error, setError] = useState('');
	const [success, setSuccess] = useState(false);

	const handleSubmit = async (e: React.FormEvent) => {
		e.preventDefault();
		setError('');
		setSuccess(false);

		if (!title.trim() || !content.trim()) {
			setError('Titulo y contenido son obligatorios');
			return;
		}

		const author = getUsernameFromToken();

		if (!author) {
			setError('Error: No se pudo obtener usuario');
			return;
		}

		setLoading(true);

		try {
			await api.post('/api/posts', {
				title: title,
				content: content,
				author: author
			});

			setSuccess(true);
			setTitle('');
			setContent('');

			onPostCreated();
		} catch (err: any) {
			setError(err.response?.data?.message || 'Error al crear post');
		} finally {
			setLoading(false);
		}
	};

	return (
		<Card className="mb-4">
			<Card.Header>Crear Nuevo Post</Card.Header>
			<Card.Body>
				{error && <Alert variant="danger">{error}</Alert>}
				{success && <Alert variant="success">Post creado exitosamente</Alert>}

				<Form onSubmit={handleSubmit}>
					<Form.Group className="mb-3">
						<Form.Label>Título</Form.Label>
						<Form.Control
							type="text"
							placeholder="Título del post"
							value={title}
							onChange={(e) => setTitle(e.target.value)}
						/>
					</Form.Group>

					<Form.Group className="mb-3">
						<Form.Label>Contenido</Form.Label>
						<Form.Control
							as="textarea"
							rows={3}
							placeholder="Escribe el contenido..."
							value={content}
							onChange={(e) => setContent(e.target.value)}
						/>
					</Form.Group>

					<Button variant="primary" type="submit" disabled={loading}>
						{loading ? 'Creando...' : 'Crear Post'}
					</Button>
				</Form>
			</Card.Body>
		</Card>
	);
};

export default CreatePostForm;
