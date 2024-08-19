import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { getMyRoleInDarakbang } from './../../apis/gets';
import { useQuery } from '@tanstack/react-query';

export default function useMyRoleInDarakbang() {
  const { data: myRoleInDarakbang, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.myRoleInDarakbang, getLastDarakbangId()],
    queryFn: () => getMyRoleInDarakbang(),
  });

  return { myRoleInDarakbang, isLoading };
}
