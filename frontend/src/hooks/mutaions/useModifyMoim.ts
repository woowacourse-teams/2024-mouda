import { useMutation, useQueryClient } from '@tanstack/react-query';
import QUERY_KEYS from '@_constants/queryKeys';
import { fetchModifyMoin } from '@_apis/fetchs';
import { TempMoimInputInfo } from '@_types/index';

interface ModifyMoimParams {
  moimId: number;
  state: TempMoimInputInfo;
}

export default function useModifyMoim(onSuccess: (moimId: number) => void) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ moimId, state }: ModifyMoimParams) =>
      fetchModifyMoin(moimId, state),
    onSuccess: (_, { moimId }) => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.moim],
      });
      onSuccess(moimId);
    },
  });
}
