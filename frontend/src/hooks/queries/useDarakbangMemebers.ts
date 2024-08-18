import QUERY_KEYS from '@_constants/queryKeys';
import { getDarakbangMembers } from '@_apis/gets';
import { useQuery } from '@tanstack/react-query';

export default function useDarakbangMembers(darakbangId: number) {
  const { data: members, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.darakbangMembers, darakbangId],
    queryFn: () => getDarakbangMembers(darakbangId),
  });

  return { members, isLoading };
}
