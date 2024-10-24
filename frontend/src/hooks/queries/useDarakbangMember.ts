import { getDarakbangMemberProfile } from '@_apis/gets';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import QUERY_KEYS from '@_constants/queryKeys';
import { useQuery } from '@tanstack/react-query';

export default function useDarakbangMember(darakbangMemberId: number) {
  const { data: member, isLoading } = useQuery({
    queryKey: [
      QUERY_KEYS.darakbang,
      getLastDarakbangId(),
      QUERY_KEYS.darakbangMember,
      darakbangMemberId,
    ],
    queryFn: () => getDarakbangMemberProfile(darakbangMemberId),
  });

  return {
    member,
    isLoading,
  };
}
