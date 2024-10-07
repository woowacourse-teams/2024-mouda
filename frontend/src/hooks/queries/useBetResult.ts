import { getBetResult } from '@_apis/gets';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import QUERY_KEYS from '@_constants/queryKeys';
import { useQuery } from '@tanstack/react-query';

export default function useBetResult(betId: number) {
  const { data: betResult, isLoading } = useQuery({
    queryKey: [
      QUERY_KEYS.darakbang,
      getLastDarakbangId(),
      QUERY_KEYS.betResult,
      betId,
    ],
    queryFn: () => getBetResult(betId),
  });

  return { betResult, isLoading };
}
