import { PropsWithChildren } from 'react';
import { useTheme } from '@emotion/react';
import FixedCreateButtonWrapper from '@_layouts/components/FixedCreationButtonWrapper/FixedCreateButtonWrapper';
import ListPageMain from './ListPageMain/ListPageMain';
import * as S from './ListpageLayout.style';
import DoubleTriHeader from '@_layouts/components/DoubleTriHeader/DoubleTriHeader';
import TriSectionHeader from '@_layouts/components/TriSectionHeader/TriSectionHeader';

function ListPageLayout(props: PropsWithChildren) {
  const { children } = props;

  const theme = useTheme();

  return <div css={S.containerStyle({ theme })}>{children}</div>;
}

ListPageLayout.DoubleTriHeader = DoubleTriHeader;
ListPageLayout.TriHeader = TriSectionHeader;
ListPageLayout.Main = ListPageMain;
ListPageLayout.ListPageFixedButtonWrapper = FixedCreateButtonWrapper;

export default ListPageLayout;
