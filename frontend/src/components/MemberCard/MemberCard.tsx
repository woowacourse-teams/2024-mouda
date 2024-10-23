import * as S from './MemberCard.style';

import UserPreview from '@_components/UserPreview/UserPreview';
import useDarakbangMember from '@_hooks/queries/useDarakbangMember';
import useProfileBottomSheet from '@_hooks/useProfileBottomSheet';
import { useTheme } from '@emotion/react';
import { Fragment } from 'react';

interface MemberCard {
  memberId: number;
  name: string;
  // options?: {
  //   description: string;
  //   onClick: () => void;
  //   hasTopBorder?: boolean;
  // }[];
  imageUrl?: string;
}

export default function MemberCard(props: MemberCard) {
  const { memberId, imageUrl, name } = props;
  const theme = useTheme();

  const { member } = useDarakbangMember(memberId);

  const { profileBottomSheet, open } = useProfileBottomSheet({
    name: member?.name || '',
    nickname: member?.nickname || '',
    description: member?.description || '',
    url: member?.url || '',
  });

  return (
    <Fragment>
      <div css={S.card} onClick={() => open()}>
        <div css={S.preview}>
          <UserPreview imageUrl={imageUrl} />
          <span css={[theme.typography.Medium, S.name]}>{name}</span>
        </div>
        {/* {options&&<div>
        <KebabMenu />
        </div>} */}
      </div>
      {profileBottomSheet}
    </Fragment>
  );
}
