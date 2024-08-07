import { getPleases } from '@_apis/gets';
import QUERY_KEYS from '@_constants/queryKeys';
import { useQuery } from '@tanstack/react-query';

export default function usePleases() {
  const { data: pleases, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.pleases],
    queryFn: () => getPleases(),
  });

  return { pleases, isLoading };
}
