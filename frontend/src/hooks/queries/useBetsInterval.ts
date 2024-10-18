import { getBets } from '@_apis/gets';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import QUERY_KEYS from '@_constants/queryKeys';
import calculateLeftMinutesUntilDeadline from '@_utils/calculateLeftMinutesUntilDeadline';
import { useQuery } from '@tanstack/react-query';

export default function useBetsInterval(intervalMs: number) {
  const { data: bets, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.darakbang, getLastDarakbangId(), QUERY_KEYS.bets],
    queryFn: async () => {
      const bets = await getBets();

      return bets.map((bet) => ({
        ...bet,
        leftMinute: calculateLeftMinutesUntilDeadline(bet.deadline),
      }));
    },

    refetchInterval: intervalMs,
  });

  return { bets, isLoading };
}
