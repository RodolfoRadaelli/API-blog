import { useState } from 'react';
import { Modal, Button, Alert } from 'react-bootstrap';
import api from '../../../api/axios';
import type { Post } from '../types/posts.types';

interface DeleteConfirmModalProps {
	show: boolean;
	post: Post | null;
	onHide: () => void;
	onDeleted: () => void
}

const DeleteConfirmModal = ({ show, post, onHide, onDeleted }: DeleteConfirmModalProps) => {
	const [loading, setLoading] = useState(false);
	const [error, setError] = useState('');

	const handleDelete = async () => {
		if (!post?.id) {
			setError('ID no válido');
			return;
		}

		setLoading(true);
		setError('');

		try {
			await api.delete(`/api/posts/${post.id}`);

			onDeleted();
			onHide();
		} catch (err: any) {
			setError(err.response?.data?.message || 'Error al eliminar');
		} finally {
			setLoading(false);
		}
	};

	return (
		<Modal show={show} onHide={onHide} centered>
			<Modal.Header closeButton>
				<Modal.Title className="text-danger">Confirmar Eliminación</Modal.Title>
			</Modal.Header>
			<Modal.Body>
				{error && <Alert variant="danger">{error}</Alert>}
				<p>Estás seguro de que deseas eliminar este post?</p>
				{post && (<div className="p-3 bg-light rounded">
					<h6>{post.title}</h6>
					<p className="mb-1 text-muted">{post.content}</p>
					<small>Por: {post.author}</small>
				</div>
				)}

				<p className="text-danger mt-3">
					<strong>Esta acción no se puede deshacer.</strong>
				</p>
			</Modal.Body>

			<Modal.Footer>
				<Button variant="secondary" onClick={onHide} disabled={loading}>
					Cancelar
				</Button>
				<Button variant="danger" onClick={handleDelete} disabled={loading}>
					{loading ? 'Eliminando...' : 'Sí, Eliminar'}
				</Button>
			</Modal.Footer>
		</Modal >
	);
};

export default DeleteConfirmModal;

