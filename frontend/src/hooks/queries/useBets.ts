import { getBets } from '@_apis/gets';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import QUERY_KEYS from '@_constants/queryKeys';
import { useQuery } from '@tanstack/react-query';

export default function useBets() {
  const { data: bets, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.darakbang, getLastDarakbangId(), QUERY_KEYS.bets],
    queryFn: getBets,
  });

  return { bets, isLoading };
}
