import { useMutation, useQueryClient } from '@tanstack/react-query';

import { MoimInputInfo } from '@_types/index';
import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { patchModifyMoim } from '@_apis/patches';

interface ModifyMoimParams {
  moimId: number;
  state: MoimInputInfo;
}

export default function useModifyMoim(onSuccess: (moimId: number) => void) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ moimId, state }: ModifyMoimParams) =>
      patchModifyMoim(moimId, state),
    onSuccess: (_, { moimId }) => {
      queryClient.invalidateQueries({
        queryKey: [
          QUERY_KEYS.darakbang,
          getLastDarakbangId(),
          QUERY_KEYS.moim,
          moimId,
        ],
      });
      onSuccess(moimId);
    },
  });
}
