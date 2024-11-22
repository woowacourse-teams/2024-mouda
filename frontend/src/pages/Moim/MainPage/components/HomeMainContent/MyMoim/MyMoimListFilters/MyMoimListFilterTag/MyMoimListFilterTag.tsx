import * as S from './MyMoimListFilterTag.style';

import { common } from '@_common/common.style';
import { useTheme } from '@emotion/react';

interface MyMoimListFilterTagProps {
  label: string;
  isSelected: boolean;
  onClick: () => void;
}

export default function MyMoimListFilterTag(props: MyMoimListFilterTagProps) {
  const { label, isSelected, onClick } = props;

  const theme = useTheme();

  return (
    <div css={[S.tag({ theme, isSelected }), common.nonDrag]} onClick={onClick}>
      {label}
    </div>
  );
}
