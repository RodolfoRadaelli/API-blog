// src/features/posts/hooks/usePosts.ts
import { useState, useEffect, useCallback } from 'react';
import api from '../../../api/axios';

interface Post {
	id: number;
	title: string;
	content: string;
	author: string;
}

interface UsePostsReturn {
	posts: Post[];
	loading: boolean;
	error: string;
	fetchPosts: () => Promise<void>;
}

export const usePosts = (refreshKey?: number): UsePostsReturn => {
	const [posts, setPosts] = useState<Post[]>([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState('');

	const fetchPosts = useCallback(async () => {
		setLoading(true);
		setError('');

		try {
			const response = await api.get('/api/posts');
			const data = response.data;
			const postsArray = Array.isArray(data) ? data : data.content || [];
			setPosts(postsArray);
		} catch (err: any) {
			setError(err.response?.data?.message || err.message || 'Error');
		} finally {
			setLoading(false);
		}
	}, []);

	useEffect(() => {
		fetchPosts();
	}, [fetchPosts, refreshKey]);

	return { posts, loading, error, fetchPosts };
};
