import './App.css';
import LoginPage from './pages/LoginPage';
import ProtectedRoute from './components/layout/ProtectedRoute';
import DashboardPage from './pages/DashboardPage';
import NavigationBar from './components/layout/Navbar';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';

function App() {

	return (
		<BrowserRouter>
			<NavigationBar />
			<Routes>
				// Rutas publicas
				<Route path="/login" element={<LoginPage />} />

				// Rutas Privada
				<Route element={<ProtectedRoute />}>
					<Route path="/dashboard" element={<DashboardPage />} />
				</Route>

				// Redirección por defecto
				<Route path="/" element={<Navigate to="/login" replace />} />

				//Error
				<Route path="*" element={<div>404 Pagina no encontrada</div>} />

			</Routes>

		</BrowserRouter>
	);

}

export default App
