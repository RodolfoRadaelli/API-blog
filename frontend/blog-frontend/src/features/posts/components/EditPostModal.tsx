import { useState, useEffect } from 'react';
import { Modal, Form, Button, Alert } from 'react-bootstrap';
import api from '../../../api/axios';
import type { Post } from '../types//posts.types';

interface EditPostModalProps {
	show: boolean;
	post: Post | null;
	onHide: () => void;
	onUpdated: () => void;
}

const EditPostModal = ({ show, post, onHide, onUpdated }: EditPostModalProps) => {
	const [title, setTitle] = useState('');
	const [content, setContent] = useState('');
	const [loading, setLoading] = useState(false);
	const [error, setError] = useState('');

	useEffect(() => {
		if (post) {
			setTitle(post.title);
			setContent(post.content);
			setError('');
		}
	}, [post]);

	const handleSave = async () => {
		if (!title.trim() || !content.trim()) {
			setError('Todos los campos son obligatorios');
			return;
		}

		setLoading(true);

		try {
			await api.put(`/api/posts/ ${post?.id}`, {
				title: title,
				content: content,
				author: post?.author
			});

			onUpdated();
			onHide();
		} catch (err: any) {
			setError(err.response?.data?.message || 'error al actualizar');
		} finally {
			setLoading(false);
		}
	};

	return (
		< Modal show={show} onHode={onHide} >
			<Modal.Header closeButton>
				<Modal.Title>
					Editar Post #{post?.id}
				</Modal.Title>
			</Modal.Header>

			<Modal.Body>
				{error && <Alert variant="danger">{error}</Alert>}

				<Form>
					<Form.Group className="mb-3">
						<Form.Label>Titulo</Form.Label>
						<Form.Control type="text" value={title} onChange={(e) => setTitle(e.target.value)} />
					</Form.Group>

					<Form.Group className="mb-3">
						<Form.Label>Contenido</Form.Label>
						<Form.Control as="textarea" rows={3} value={content} onChange={(e) => setContent(e.target.value)} />
					</Form.Group>
				</Form>
			</Modal.Body>

			<Modal.Footer>
				<Button variant="secondary" onClick={onHide}>
					Cancelar
				</Button>
				<Button variant="primary" onClick={handleSave} disabled={loading}>
					{loading ? 'guardando...' : 'Guardar Cambios'}
				</Button>
			</Modal.Footer>
		</Modal>
	);

};

export default EditPostModal;
