import { Filter } from '@_components/MyMoimListFilters/MyMoimListFilters';
import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { getMyFilteredMoims } from '@_apis/gets';
import { useQuery } from '@tanstack/react-query';

export default function useMyMoims(selectedFilter: Filter['api']) {
  const { data: myMoims, isLoading } = useQuery({
    queryKey: [
      QUERY_KEYS.darakbang,
      getLastDarakbangId(),
      QUERY_KEYS.myMoims,
      selectedFilter,
    ],
    queryFn: () => getMyFilteredMoims(selectedFilter),
  });

  return { myMoims, isLoading };
}
