import { QueryCache, QueryClient } from '@tanstack/react-query';

const createQueryClient = () => {
  return new QueryClient({
    defaultOptions: {
      queries: {
        networkMode: 'always',
      },
    },
    queryCache: new QueryCache({
      onError: (error) => {
        alert(error);
      },
    }),
  });
};
export default createQueryClient;
