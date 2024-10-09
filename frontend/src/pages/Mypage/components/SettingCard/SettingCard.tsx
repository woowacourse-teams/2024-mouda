import * as S from './SettingCard.style';
import { useTheme } from '@emotion/react';
import { HTMLProps } from 'react';

interface SettingCardProps extends HTMLProps<HTMLDivElement> {
  title: string;
  onClick: () => void;
}

export default function SettingCard(props: SettingCardProps) {
  const { title, onClick } = props;
  const theme = useTheme();
  return (
    <li css={S.CardBox({ theme })} onClick={onClick}>
      <div css={S.Title({ theme })}>{title}</div>
    </li>
  );
}
