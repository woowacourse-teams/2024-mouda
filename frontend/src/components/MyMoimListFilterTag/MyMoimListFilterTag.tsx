import { useTheme } from '@emotion/react';
import * as S from './MyMoimListFilterTag.style';

interface MyMoimListFilterTagProps {
  label: string;
  isSelected: boolean;
  onClick: () => void;
}

export default function MyMoimListFilterTag(props: MyMoimListFilterTagProps) {
  const { label, isSelected, onClick } = props;

  const theme = useTheme();

  return (
    <div css={S.tag({ theme, isSelected })} onClick={onClick}>
      {label}
    </div>
  );
}
