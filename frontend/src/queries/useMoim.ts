import QUERY_KEYS from '../queryKeys';
import { getMoims } from '../apis/gets';
import { useQuery } from '@tanstack/react-query';

export default function useMoims() {
  const { data: moims, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.moims],
    queryFn: getMoims,
  });

  return { moims, isLoading };
}
