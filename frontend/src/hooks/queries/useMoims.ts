import QUERY_KEYS from '@_constants/queryKeys';
import { getMoims } from '@_apis/gets';
import { useMemo } from 'react';
import { useQuery } from '@tanstack/react-query';

export default function useMoims() {
  const { data: moims, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.moims],
    queryFn: getMoims,
  });
  // TODO:서버에서 검증하면 없애야 함
  const filteredMoims = useMemo(
    () =>
      moims?.filter((moim) => {
        const nowDate = new Date();
        const nowDateYyyymmdd = `${nowDate.getFullYear()}-${(nowDate.getMonth() + 1).toString().padStart(2, '00')}-${nowDate.getDate().toString().padStart(2, '00')}`;
        return moim.date >= nowDateYyyymmdd;
      }),
    [moims],
  );
  return { moims: filteredMoims, isLoading };
}
