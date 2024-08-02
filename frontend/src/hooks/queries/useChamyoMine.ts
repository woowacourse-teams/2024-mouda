import QUERY_KEYS from '@_constants/queryKeys';
import { getChamyoMine } from '@_apis/gets';
import { useQuery } from '@tanstack/react-query';

export default function useChamyoMine(moimId: number) {
  const { data: role, isLoading: isChamyoMineLoading } = useQuery({
    queryKey: [QUERY_KEYS.chamyoMine, moimId],
    queryFn: () => getChamyoMine(moimId),
  });

  return { role, isChamyoMineLoading };
}
