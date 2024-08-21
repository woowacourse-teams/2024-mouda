import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { getMoim } from '@_apis/gets';
import { useQuery } from '@tanstack/react-query';

export default function useMoim(moimId: number) {
  const { data: moim, isLoading } = useQuery({
    queryKey: [
      QUERY_KEYS.darakbang,
      getLastDarakbangId(),
      QUERY_KEYS.moim,
      moimId,
    ],
    queryFn: () => getMoim(moimId),
  });

  return { moim, isLoading };
}
