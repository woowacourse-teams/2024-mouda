import QUERY_KEYS from '@_constants/queryKeys';
import { getMoims } from '@_apis/gets';
import { useQuery } from '@tanstack/react-query';

export default function useMoims() {
  const { data: moims, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.moims],
    queryFn: getMoims,
  });
  return { moims, isLoading };
}
