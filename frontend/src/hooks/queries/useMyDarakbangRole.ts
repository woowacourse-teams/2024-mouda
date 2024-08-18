import QUERY_KEYS from '@_constants/queryKeys';
import { getMyRoleInDarakbang } from './../../apis/gets';
import { useQuery } from '@tanstack/react-query';

export default function useMyRoleInDarakbang(darakbangId: number) {
  const { data: myRoleInDarakbang, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.myRoleInDarakbang],
    queryFn: () => getMyRoleInDarakbang(darakbangId),
  });

  return { myRoleInDarakbang, isLoading };
}
