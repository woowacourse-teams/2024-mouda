import QUERY_KEYS from '@_constants/queryKeys';
import { getMyInfo } from '@_apis/gets';
import { useQuery } from '@tanstack/react-query';

export default function useMyInfo() {
  const { data: myInfo, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.myInfo],
    queryFn: getMyInfo,
  });

  return { myInfo, isLoading };
}
