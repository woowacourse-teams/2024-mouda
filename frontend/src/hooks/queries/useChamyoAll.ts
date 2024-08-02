import QUERY_KEYS from '@_constants/queryKeys';
import { getChamyoAll } from '@_apis/gets';
import { useQuery } from '@tanstack/react-query';

export default function useChamyoAll(moimId: number) {
  const { data: participants, isLoading: chamyoAllIsLoading } = useQuery({
    queryKey: [QUERY_KEYS.chamyoAll, moimId],
    queryFn: () => getChamyoAll(moimId),
  });

  return { participants, chamyoAllIsLoading };
}
