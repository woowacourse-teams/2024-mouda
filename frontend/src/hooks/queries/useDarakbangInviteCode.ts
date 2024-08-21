import QUERY_KEYS from '@_constants/queryKeys';
import { getDarakbangInviteCode } from '@_apis/gets';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { useQuery } from '@tanstack/react-query';

export default function useDarakbangInviteCode() {
  const { data: inviteCode, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.darakbangInviteCode, getLastDarakbangId()],
    queryFn: () => getDarakbangInviteCode(),
  });

  return { inviteCode, isLoading };
}
