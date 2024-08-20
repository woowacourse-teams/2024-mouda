import { PropsWithChildren } from 'react';
import * as S from './FunnelRadioCardGroup.style';
import FunnelRadioCardGroupOption from './FunnelRadioCardGroupOption/FunnelRadioCardGroupOption';

function FunnelRadioCardGroup(props: PropsWithChildren) {
  const { children } = props;

  return <div css={S.container}>{children}</div>;
}

FunnelRadioCardGroup.Option = FunnelRadioCardGroupOption;

export default FunnelRadioCardGroup;
