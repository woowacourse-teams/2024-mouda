import { PropsWithChildren } from 'react';
import * as S from './FunnelRadioCardGroup.style';
import FunnelRadioCardGroupOption from './FunnelRadioCardGroupOption/FunnelRadioCardGroupOption';

interface FunnelRadioCardGroupProps {
  columns?: number;
}
function FunnelRadioCardGroup(
  props: PropsWithChildren<FunnelRadioCardGroupProps>,
) {
  const { children, columns = 1 } = props;

  return <div css={S.container({ columns })}>{children}</div>;
}

FunnelRadioCardGroup.Option = FunnelRadioCardGroupOption;

export default FunnelRadioCardGroup;
