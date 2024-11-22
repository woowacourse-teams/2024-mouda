import * as S from './MenuItem.style';

import SolidArrow from '@_components/Icons/SolidArrow';
import { useTheme } from '@emotion/react';

interface MenuItemProps {
  description: string;
  onClick: () => void;
  isLastItem?: boolean;
}

export default function MenuItem(props: MenuItemProps) {
  const { description, onClick, isLastItem = false } = props;
  const theme = useTheme();
  return (
    <div css={S.item({ theme, isLastItem })} onClick={onClick}>
      <span css={theme.typography.s1}>{description}</span>
      <SolidArrow direction="right" />
    </div>
  );
}
