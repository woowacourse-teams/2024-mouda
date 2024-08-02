import { getMyMoims } from '@_apis/gets';
import { Filter } from '@_components/MyMoimListFilters/MyMoimListFilters';
import QUERY_KEYS from '@_constants/queryKeys';
import { useQuery } from '@tanstack/react-query';

export default function useMyMoims(selectedFilter: Filter['api']) {
  const { data: myMoims, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.myMoims, selectedFilter],
    queryFn: () => getMyMoims(selectedFilter),
  });

  return { myMoims, isLoading };
}
