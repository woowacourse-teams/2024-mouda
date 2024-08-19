import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { getZzimMine } from '@_apis/gets';
import { useQuery } from '@tanstack/react-query';

export default function useZzimMine(moimId: number) {
  const { data: isZzimed, isLoading: isZzimMineLoading } = useQuery({
    queryKey: [
      QUERY_KEYS.darakbang,
      getLastDarakbangId(),
      QUERY_KEYS.ZzimMine,
      moimId,
    ],
    queryFn: () => getZzimMine(moimId),
  });

  return { isZzimed, isZzimMineLoading };
}
