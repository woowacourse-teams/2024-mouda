import QUERY_KEYS from '@_constants/queryKeys';
import { getMyMoims } from '@_apis/gets';
import { useQuery } from '@tanstack/react-query';

export default function useMyMoims() {
  const { data: moims, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.myMoim],
    queryFn: getMyMoims,
  });

  return { moims, isLoading };
}
