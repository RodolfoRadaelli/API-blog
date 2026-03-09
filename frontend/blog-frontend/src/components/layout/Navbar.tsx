import { Container, Navbar, Nav, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { getUsernameFromToken } from '../../utils/jwt';

const NavigationBar = () => {
	const navigate = useNavigate();
	const [username, setUsername] = useState<string | null>(null);
	const token = localStorage.getItem('token');

	useEffect(() => {
		if (token) {
			const user = getUsernameFromToken();
			setUsername(user);
		}
	}, [token]);

	const handleLogout = () => {
		localStorage.removeItem('token');
		setUsername(null);
		navigate('/login');
	};

	if (!token) return null;

	return (
		<Navbar bg="dark" variant="dark" className="mb-4">
			<Container>
				<Navbar.Brand href="/dashboard">Mi Blog</Navbar.Brand>

				<Nav className="me-auto">
					<Nav.Link href="/dashboard">Dashboard</Nav.Link>
				</Nav>

				<Nav>
					<Navbar.Text className="me-3 text-light">
						Usuario: <strong>{username || 'Usuario'}</strong>
					</Navbar.Text>
					<Button variant="outline-light" size="sm" onClick={handleLogout}>
						Cerrar sesión
					</Button>
				</Nav>
			</Container>
		</Navbar>
	);

};

export default NavigationBar;
