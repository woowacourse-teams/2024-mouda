import useDarakbangMember from '@_hooks/queries/useDarakbangMember';
import { preview } from './ChatProfileImage.style';
import useProfileBottomSheet from '@_hooks/useProfileBottomSheet';
import { Fragment } from 'react';

interface UserPreviewProps {
  memberId: number;
  imageUrl?: string;
  size?: string;
  hasCrown?: boolean;
}

export default function ChatProfileImage(props: UserPreviewProps) {
  const { memberId, imageUrl, size } = props;

  const { member } = useDarakbangMember(memberId);

  const { profileBottomSheet, open } = useProfileBottomSheet({
    name: member?.name || '',
    nickname: member?.nickname || '',
    description: member?.description || '',
    url: member?.profile || '',
  });

  return (
    <Fragment>
      <div css={preview({ imageUrl, size })} onClick={() => open()} />
      {profileBottomSheet}
    </Fragment>
  );
}
