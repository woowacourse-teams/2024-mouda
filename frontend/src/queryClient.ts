import { QueryCache, QueryClient } from '@tanstack/react-query';

const createQueryClient = () => {
  return new QueryClient({
    defaultOptions: {
      queries: {
        networkMode: 'always',
      },
      mutations: {
        onError: (error) => alert(error.message),
        networkMode: 'always',
      },
    },
    queryCache: new QueryCache({
      onError: (error) => {
        alert(error instanceof Error ? error.message : 'An error occurred');
      },
    }),
  });
};
export default createQueryClient;
