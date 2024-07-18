import QUERY_KEYS from '@_constants/queryKeys';
import { getMoim } from '@_apis/gets';
import { useQuery } from '@tanstack/react-query';

export default function useMoim(moimId: number) {
  const { data: moim, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.moim],
    queryFn: () => getMoim(moimId),
  });

  return { moim, isLoading };
}
