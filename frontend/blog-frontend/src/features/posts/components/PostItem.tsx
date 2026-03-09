import { Button } from 'react-bootstrap';
import type { Post } from '../types/posts.types';

interface PostItemProps {
	post: Post;
	currentUser: string | null;
	currentRole: string | null;
	onEdit: (post: Post) => void;
	onDelete: (post: Post) => void;
}

export const PostItem = ({ post, currentUser, currentRole, onEdit, onDelete }: PostItemProps) => {
	const isAdmin = currentRole === 'ROLE_ADMIN';
	const isAuthor = post.author === currentUser;
	const canEdit = isAdmin || isAuthor;
	const canDelete = isAdmin || isAuthor;

	return (
		<div className="d-flex justify-content-between align-items-start">
			<div>
				<h5>{post.title}</h5>
				<p className="mb-1">{post.content}</p>
				<small className="text-muted">
					Por: {post.author}
					{isAuthor && <span className="badge bg-primary ms-1">Tú</span>}
				</small>
			</div>

			<div className="ms-2">
				{canEdit && (
					<Button
						variant="outline-primary"
						size="sm"
						className="me-1"
						onClick={() => onEdit(post)}
					>
						Editar
					</Button>
				)}
				{canDelete && (
					<Button
						variant="outline-danger"
						size="sm"
						onClick={() => onDelete(post)}
					>
						Eliminar
					</Button>
				)}
			</div>
		</div>
	);
};
