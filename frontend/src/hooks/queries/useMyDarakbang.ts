import QUERY_KEYS from '@_constants/queryKeys';
import { getMyDarakbangs } from '@_apis/gets';
import { useQuery } from '@tanstack/react-query';

export default function useMyDarakbangs() {
  const { data: myDarakbangs, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.myDarakbangs],
    queryFn: getMyDarakbangs,
  });

  return { myDarakbangs, isLoading };
}
