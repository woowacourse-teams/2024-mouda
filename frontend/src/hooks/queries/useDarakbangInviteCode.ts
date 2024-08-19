import QUERY_KEYS from '@_constants/queryKeys';
import { getDarakbangInviteCode } from '@_apis/gets';
import { useQuery } from '@tanstack/react-query';

export default function useDarakbangInviteCode(darakbangId: number) {
  const { data: inviteCode, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.darakbangInviteCode, darakbangId],
    queryFn: () => getDarakbangInviteCode(darakbangId),
  });

  return { inviteCode, isLoading };
}
