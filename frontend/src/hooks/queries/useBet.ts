import { getBet } from '@_apis/gets';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import QUERY_KEYS from '@_constants/queryKeys';
import { useQuery } from '@tanstack/react-query';

export default function useBet(betId: number) {
  const { data: bet, isLoading } = useQuery({
    queryKey: [
      QUERY_KEYS.darakbang,
      getLastDarakbangId(),
      QUERY_KEYS.bet,
      betId,
    ],
    queryFn: () => getBet(betId),
  });

  return { bet, isLoading };
}
