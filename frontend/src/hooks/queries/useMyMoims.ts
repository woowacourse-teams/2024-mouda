import { getMyMoims } from '@_apis/gets';
import QUERY_KEYS from '@_constants/queryKeys';
import { useQuery } from '@tanstack/react-query';

export default function useMyMoims() {
  const { data: myMoims, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.myMoims],
    queryFn: getMyMoims,
  });

  return { myMoims, isLoading };
}
