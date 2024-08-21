import QUERY_KEYS from '@_constants/queryKeys';
import { getDarakbangNameById } from '@_apis/gets';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { useQuery } from '@tanstack/react-query';

export default function useNowDarakbangName() {
  const { data: darakbangName, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.darakbangNameByCode, getLastDarakbangId()],
    queryFn: () => getDarakbangNameById(),
  });

  return { darakbangName, isLoading };
}
