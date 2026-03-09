import { useState } from 'react';
import { Card, ListGroup, Spinner, Alert, Button } from 'react-bootstrap';
import type { Post } from '../types/posts.types';
import EditPostModal from './EditPostModal';
import DeleteConfirmModal from './DeleteConfirmModal';
import { getUsernameFromToken, getRoleFromToken } from '../../../utils/jwt';
import { usePosts } from '../hooks/usePosts';

interface PostsListProps {
	refreshKey?: number;
}

const PostsList = ({ refreshKey }: PostsListProps = {}) => {
	const { posts, loading, error, fetchPosts } = usePosts(refreshKey);

	const [showEditModal, setShowEditModal] = useState(false);
	const [selectedPost, setSelectedPost] = useState<Post | null>(null);
	const [showDeleteModal, setShowDeleteModal] = useState(false);

	const currentUser = getUsernameFromToken();
	const currentRole = getRoleFromToken();
	const isAdmin = currentRole === 'ROLE_ADMIN';

	const handleEdit = (post: Post) => {
		setSelectedPost(post);
		setShowEditModal(true);
	};

	const handleDeleteClick = (post: Post) => {
		setSelectedPost(post);
		setShowDeleteModal(true);
	}

	const canEdit = (author: string): boolean => {
		return isAdmin || author === currentUser;
	}

	const canDelete = (author: string): boolean => {
		return isAdmin || author === currentUser;
	};

	const isMyPost = (author: string): boolean => {
		return author === currentUser;
	}

	if (loading)
		return <Spinner animation="border" className="d-block mx-auto mt-5" />;
	if (error)
		return <Alert variant="danger">{error}</Alert>;
	if (posts.length === 0)
		return <Alert variant="info">No hay posts</Alert>;

	return (
		<>
			<Card>
				<Card.Header>
					Lista de Posts ({posts.length})
					{isAdmin && <span className="badge bg-danger ms-2">ADMIN</span>}
				</Card.Header>
				<ListGroup variant="flush">
					{posts.map(post => (
						<ListGroup.Item key={post.id}>
							<div className="d-flex justify-content-between align-items-start">
								<div>
									<h5>{post.title}</h5>
									<p className="mb-1">{post.content}</p>
									<small className="text-muted">Por: {post.author} {isMyPost(post.author) && (<span className="badge bg-primary ms-1">Tú</span>
									)}
									</small>
								</div>
								<div className="ms-2">
									{canEdit(post.author) && (
										<Button variant="outline-primary" size="sm" className="me-1" onClick={() => handleEdit(post)}>
											Editar
										</Button>
									)}
									{canDelete(post.author) && (
										<Button variant="outline-danger" size="sm" onClick={() => handleDeleteClick(post)}>
											Eliminar
										</Button>
									)}
								</div>
							</div>

						</ListGroup.Item>
					))}
				</ListGroup>
			</Card>

			<EditPostModal show={showEditModal} post={selectedPost} onHide={() => setShowEditModal(false)} onUpdated={fetchPosts} />

			<DeleteConfirmModal show={showDeleteModal} post={selectedPost}
				onHide={() => setShowDeleteModal(false)}
				onDeleted={fetchPosts}
			/>
		</>
	);
};

export default PostsList;
