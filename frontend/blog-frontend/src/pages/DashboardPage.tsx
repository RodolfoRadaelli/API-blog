import { useState } from 'react';
import { Container, Row, Col, } from 'react-bootstrap';
import PostsList from '../features/posts/components//PostsList';
import CreatePostForm from '../features/posts/components/CreatePostForm';

const DashboardPage = () => {
	const [refreshKey, setRefreshKey] = useState(0);

	const handlePostCreated = () => {
		setRefreshKey(prev => prev + 1);
	};

	return (
		<Container className="mt-4">
			<Row className="mb-4">
				<Col>
					<h2>Dashboard</h2>
					<p className="text-muted">Gestiona tus posts</p>
				</Col>
			</Row>

			<Row>
				<Col lg={8} className="mx-auto">
					<CreatePostForm onPostCreated={handlePostCreated} />
					<PostsList key={refreshKey} />
				</Col>
			</Row>
		</Container >
	);
};

export default DashboardPage;
