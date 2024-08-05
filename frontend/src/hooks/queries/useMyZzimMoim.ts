import { getMyZzimMoims } from '@_apis/gets';
import QUERY_KEYS from '@_constants/queryKeys';
import { useQuery } from '@tanstack/react-query';

export default function useMyZzimMoims() {
  const { data: myZzimMoims, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.myZzimMoims],
    queryFn: getMyZzimMoims,
  });

  return { myZzimMoims, isLoading };
}
