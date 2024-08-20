import QUERY_KEYS from '@_constants/queryKeys';
import { getDarakbangMembers } from '@_apis/gets';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { useQuery } from '@tanstack/react-query';

export default function useDarakbangMembers() {
  const { data: members, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.darakbangMembers, getLastDarakbangId()],
    queryFn: () => getDarakbangMembers(),
  });

  return { members, isLoading };
}
