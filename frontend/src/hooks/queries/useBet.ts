import QUERY_KEYS from '@_constants/queryKeys';
import { getBet } from '@_apis/gets';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { useQuery } from '@tanstack/react-query';

export default function useBet(betId: number) {
  const {
    data: bet,
    isLoading,
    isFetching,
  } = useQuery({
    queryKey: [
      QUERY_KEYS.darakbang,
      getLastDarakbangId(),
      QUERY_KEYS.bet,
      betId,
    ],
    queryFn: () => getBet(betId),
    staleTime: 1000,
    refetchInterval: 1000,
  });

  return { bet, isLoading, isFetching };
}
