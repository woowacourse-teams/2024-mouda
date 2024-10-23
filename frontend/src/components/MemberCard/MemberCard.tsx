import * as S from './MemberCard.style';

import UserPreview from '@_components/UserPreview/UserPreview';
import { useTheme } from '@emotion/react';

interface MemberCard {
  name: string;
  // options?: {
  //   description: string;
  //   onClick: () => void;
  //   hasTopBorder?: boolean;
  // }[];
  imageUrl?: string;
}

export default function MemberCard(props: MemberCard) {
  const { imageUrl, name } = props;
  const theme = useTheme();

  return (
    <div css={S.card} onClick={() => alert(1)}>
      <div css={S.preview}>
        <UserPreview imageUrl={imageUrl} />
        <span css={[theme.typography.Medium, S.name]}>{name}</span>
      </div>
      {/* {options&&<div>
        <KebabMenu />
      </div>} */}
    </div>
  );
}
