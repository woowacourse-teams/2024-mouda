import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { getMoims } from '@_apis/gets';
import { useQuery } from '@tanstack/react-query';

export default function useMoims() {
  const { data: moims, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.darakbang, getLastDarakbangId(), QUERY_KEYS.moims],
    queryFn: getMoims,
  });
  return { moims, isLoading };
}
