import QUERY_KEYS from '@_constants/queryKeys';
import { getDarakbangNameByCode } from '@_apis/gets';
import { useQuery } from '@tanstack/react-query';

export default function useDarakbangNameByCode(code: string) {
  const { data: darakbangName, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.darakbangNameByCode, code],
    queryFn: () => getDarakbangNameByCode(code),
  });

  return { darakbangName, isLoading };
}
